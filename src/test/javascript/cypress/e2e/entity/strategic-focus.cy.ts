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

describe('StrategicFocus e2e test', () => {
  const strategicFocusPageUrl = '/strategic-focus';
  const strategicFocusPageUrlPattern = new RegExp('/strategic-focus(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const strategicFocusSample = { value: 'Tools Arkansas', language: 'GREEK' };

  let strategicFocus;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/strategic-foci+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/strategic-foci').as('postEntityRequest');
    cy.intercept('DELETE', '/api/strategic-foci/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (strategicFocus) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/strategic-foci/${strategicFocus.id}`,
      }).then(() => {
        strategicFocus = undefined;
      });
    }
  });

  it('StrategicFoci menu should load StrategicFoci page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('strategic-focus');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StrategicFocus').should('exist');
    cy.url().should('match', strategicFocusPageUrlPattern);
  });

  describe('StrategicFocus page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(strategicFocusPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StrategicFocus page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/strategic-focus/new$'));
        cy.getEntityCreateUpdateHeading('StrategicFocus');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', strategicFocusPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/strategic-foci',
          body: strategicFocusSample,
        }).then(({ body }) => {
          strategicFocus = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/strategic-foci+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/strategic-foci?page=0&size=20>; rel="last",<http://localhost/api/strategic-foci?page=0&size=20>; rel="first"',
              },
              body: [strategicFocus],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(strategicFocusPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StrategicFocus page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('strategicFocus');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', strategicFocusPageUrlPattern);
      });

      it('edit button click should load edit StrategicFocus page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StrategicFocus');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', strategicFocusPageUrlPattern);
      });

      it('edit button click should load edit StrategicFocus page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StrategicFocus');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', strategicFocusPageUrlPattern);
      });

      it('last delete button click should delete instance of StrategicFocus', () => {
        cy.intercept('GET', '/api/strategic-foci/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('strategicFocus').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', strategicFocusPageUrlPattern);

        strategicFocus = undefined;
      });
    });
  });

  describe('new StrategicFocus page', () => {
    beforeEach(() => {
      cy.visit(`${strategicFocusPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StrategicFocus');
    });

    it('should create an instance of StrategicFocus', () => {
      cy.get(`[data-cy="value"]`).type('expedite').should('have.value', 'expedite');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        strategicFocus = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', strategicFocusPageUrlPattern);
    });
  });
});
