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

describe('Kpis e2e test', () => {
  const kpisPageUrl = '/kpis';
  const kpisPageUrlPattern = new RegExp('/kpis(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const kpisSample = { value: 'driver', checkBoxValue: true, language: 'GREEK' };

  let kpis;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/kpis+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/kpis').as('postEntityRequest');
    cy.intercept('DELETE', '/api/kpis/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (kpis) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/kpis/${kpis.id}`,
      }).then(() => {
        kpis = undefined;
      });
    }
  });

  it('Kpis menu should load Kpis page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('kpis');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Kpis').should('exist');
    cy.url().should('match', kpisPageUrlPattern);
  });

  describe('Kpis page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(kpisPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Kpis page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/kpis/new$'));
        cy.getEntityCreateUpdateHeading('Kpis');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kpisPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/kpis',
          body: kpisSample,
        }).then(({ body }) => {
          kpis = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/kpis+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/kpis?page=0&size=20>; rel="last",<http://localhost/api/kpis?page=0&size=20>; rel="first"',
              },
              body: [kpis],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(kpisPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Kpis page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('kpis');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kpisPageUrlPattern);
      });

      it('edit button click should load edit Kpis page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Kpis');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kpisPageUrlPattern);
      });

      it('edit button click should load edit Kpis page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Kpis');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kpisPageUrlPattern);
      });

      it('last delete button click should delete instance of Kpis', () => {
        cy.intercept('GET', '/api/kpis/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('kpis').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kpisPageUrlPattern);

        kpis = undefined;
      });
    });
  });

  describe('new Kpis page', () => {
    beforeEach(() => {
      cy.visit(`${kpisPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Kpis');
    });

    it('should create an instance of Kpis', () => {
      cy.get(`[data-cy="value"]`).type('integrated').should('have.value', 'integrated');

      cy.get(`[data-cy="checkBoxValue"]`).should('not.be.checked');
      cy.get(`[data-cy="checkBoxValue"]`).click().should('be.checked');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        kpis = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', kpisPageUrlPattern);
    });
  });
});
