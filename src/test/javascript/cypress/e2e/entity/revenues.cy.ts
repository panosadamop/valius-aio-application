import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Revenues e2e test', () => {
  const revenuesPageUrl = '/revenues';
  const revenuesPageUrlPattern = new RegExp('/revenues(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const revenuesSample = { value: 'Facilitator Soft', language: 'ENGLISH' };

  let revenues;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/revenues+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/revenues').as('postEntityRequest');
    cy.intercept('DELETE', '/api/revenues/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (revenues) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/revenues/${revenues.id}`,
      }).then(() => {
        revenues = undefined;
      });
    }
  });

  it('Revenues menu should load Revenues page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('revenues');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Revenues').should('exist');
    cy.url().should('match', revenuesPageUrlPattern);
  });

  describe('Revenues page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(revenuesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Revenues page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/revenues/new$'));
        cy.getEntityCreateUpdateHeading('Revenues');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', revenuesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/revenues',
          body: revenuesSample,
        }).then(({ body }) => {
          revenues = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/revenues+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/revenues?page=0&size=20>; rel="last",<http://localhost/api/revenues?page=0&size=20>; rel="first"',
              },
              body: [revenues],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(revenuesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Revenues page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('revenues');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', revenuesPageUrlPattern);
      });

      it('edit button click should load edit Revenues page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Revenues');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', revenuesPageUrlPattern);
      });

      it('edit button click should load edit Revenues page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Revenues');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', revenuesPageUrlPattern);
      });

      it('last delete button click should delete instance of Revenues', () => {
        cy.intercept('GET', '/api/revenues/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('revenues').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', revenuesPageUrlPattern);

        revenues = undefined;
      });
    });
  });

  describe('new Revenues page', () => {
    beforeEach(() => {
      cy.visit(`${revenuesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Revenues');
    });

    it('should create an instance of Revenues', () => {
      cy.get(`[data-cy="value"]`).type('envisioneer connecting open-source').should('have.value', 'envisioneer connecting open-source');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        revenues = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', revenuesPageUrlPattern);
    });
  });
});
